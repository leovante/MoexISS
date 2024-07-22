FROM postgres

USER root
COPY ./Storage/postgress_db/scripts/ /docker-entrypoint-initdb.d/
#USER superset

MAINTAINER Temnikov Dmitry