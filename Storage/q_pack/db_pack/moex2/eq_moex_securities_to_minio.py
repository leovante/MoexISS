import io
import json
from io import StringIO

import boto3
import pandas as pd
import requests

import q_credentials.api_minio as api_minio
import q_credentials.api_moex as api_moex


class JsonDataStream:
    def __init__(self):
        self.url = None
        self.session = requests.Session()
        self.pre_host = "http://"
        self.s3 = boto3.client('s3',
                               endpoint_url=self.pre_host + api_minio.host + ":" + api_minio.port,
                               aws_access_key_id=api_minio.access_key,
                               aws_secret_access_key=api_minio.secret_key)
        self.moex_url = self.pre_host + api_moex.host + ':' + api_moex.port + api_moex.get_securities_candles_uri
        self.Bucket = api_minio.bucket_airflow_files

    def get_moex(self, csv: StringIO, security):
        try:
            response = self.session.get(self.url)
            if response.status_code != 200:
                raise Exception("Failed to fetch data")
            data_list = json.loads(response.text)
            df = pd.DataFrame(data_list, columns=['open', 'close', 'high', 'low', 'value', 'volume', 'begin', 'end'])
            df = df[['begin', 'open', 'high', 'low', 'close', 'volume']]
            df['ticker'] = security  # Добавляем столбец 'ticker' с именем тикера
            df.set_index('begin', inplace=True)

            df.dropna(inplace=True)
            df.to_csv(csv,
                      mode='a',
                      header=False)  # Используем mode='a' для добавления данных в файл без перезаписи

        except Exception as e:
            print(f"Error loading data: {e}")
            return False

    def securities(self):
        minio_key_interested = "interested_tickers_Zhenya.xlsx"
        minio_key_downloaded = "downloads_frame_Zhenya.csv"

        object = self.s3.get_object(Bucket=self.Bucket, Key=minio_key_interested)
        data = io.BytesIO(object['Body']._raw_stream.data)
        df_tickers = pd.read_excel(data, sheet_name="daily", usecols="A", header=None, skiprows=1)
        fromm = pd.read_excel(data, sheet_name="daily", usecols="B", header=None).iat[0, 0].date()

        csv_buffer = StringIO()
        for security in df_tickers[0]:
            # Формирование полного URL с текущим тикером
            self.url = self.moex_url.replace('{security}', security) + f'?from={fromm}&interval=24'
            # Создание экземпляра JsonDataStream с текущим URL и добавление его в cerebro
            self.get_moex(csv_buffer, security)

        self.s3.put_object(Bucket=self.Bucket, Key=minio_key_downloaded, Body=csv_buffer.getvalue())


def main():
    JsonDataStream().securities()


if __name__ == "__main__":
    main()
