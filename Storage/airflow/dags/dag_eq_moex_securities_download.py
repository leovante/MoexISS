from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from db_pack.moex2 import eq_moex_securities

DAG_DEFAULT_ARGS = {
    'owner': 'airflow',
    'depends_on_past': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=1)
}

with DAG('eq_moex_securities_download', start_date=datetime(2024, 7, 23), schedule_interval="0 7 * * *",
         default_args=DAG_DEFAULT_ARGS, catchup=False) as dag:
    updating_db_daily = PythonOperator(task_id="updating_db_moex_daily", python_callable=eq_moex_securities.main)

    updating_db_daily
