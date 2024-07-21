FROM amancevice/superset:0.35.2

USER root
COPY ./Storage/superset/superset_config.py /etc/superset/superset_config.py
COPY ./Storage/superset/superset.db /var/lib/superset/superset.db
RUN chmod 777 /var/lib/superset/superset.db
USER superset

MAINTAINER Temnikov Dmitry