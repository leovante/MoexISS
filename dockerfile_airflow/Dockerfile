FROM puckel/docker-airflow:1.10.7

USER root
COPY ./dockerfile_airflow/requirements.txt /airflow/requirements.txt
COPY ./Storage/airflow/dags /usr/local/airflow/dags
COPY ./Storage/q_pack /usr/local/airflow/dags/q_pack
RUN pip install -r /airflow/requirements.txt

ENV PYTHONPATH "${PYTHONPATH}:/usr/local/airflow/dags/q_pack"
# For airflow container defining the environment variables in the docker compose doesnt work except for the airflow variables
ENV MLFLOW_S3_ENDPOINT_URL "http://minio-image:9000"
ENV AWS_ACCESS_KEY_ID "minio-image"
ENV AWS_SECRET_ACCESS_KEY "minio-image-pass"
ENV MLFLOW_TRACKING_URI "http://mlflow-image:5500"