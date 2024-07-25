from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import timedelta, datetime

from db_pack.schema import risk_db_schema_builder
from db_pack.schema import secmaster_db_schema_builder

DAG_DEFAULT_ARGS={
    'owner':'airflow',
    'depends_on_past':False,
    'retries':1,
    'retry_delay':timedelta(minutes=1)
}

with DAG('fx_db_init', start_date=datetime(2019,1,1), schedule_interval=None,default_args=DAG_DEFAULT_ARGS, catchup=False) as dag:

    risk_db_schema_builder_fx = PythonOperator(task_id="risk_db_schema_builder_fx",python_callable=risk_db_schema_builder.main)

    secmaster_db_schema_builder_fx = PythonOperator(task_id="secmaster_db_schema_builder_fx",python_callable=secmaster_db_schema_builder.main)

    risk_db_schema_builder_fx >> secmaster_db_schema_builder_fx
