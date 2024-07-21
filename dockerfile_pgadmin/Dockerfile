FROM dpage/pgadmin4:7

USER root
COPY ./Storage/pgadmin2/pgadmin4.db /var/lib/pgadmin/pgadmin4.db
COPY ./Storage/pgadmin2/sessions /var/lib/pgadmin/sessions
RUN chmod 777 /var/lib/pgadmin/pgadmin4.db
# USER pgadmin

MAINTAINER Temnikov Dmitry