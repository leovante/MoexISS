from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from db_pack.moex2 import eq_moex_securities_to_minio

DAG_DEFAULT_ARGS = {
    'owner': 'airflow',
    'depends_on_past': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=1)
}

with DAG('eq_moex_securities_download_to_minio',
         start_date=datetime(2019, 1, 1),
         schedule_interval="0 7 * * *",
         default_args=DAG_DEFAULT_ARGS, catchup=False) as dag:
    get_db_moex_dict_to_minio = PythonOperator(task_id="get_db_moex_dict_to_minio",
                                               python_callable=eq_moex_securities_to_minio.main)

    get_db_moex_dict_to_minio
