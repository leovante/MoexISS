FROM jupyter/scipy-notebook

USER root
#COPY ./Storage/notebooks /home/jovyan/work
#COPY ./Storage/q_pack /home/jovyan/work/q_pack
#COPY ./Storage/mlflow /mlflow-image

COPY ./dockerfile_jupyter_notebook/requirements.txt /tmp/
RUN pip install --default-timeout=100 --requirement /tmp/requirements.txt -i https://pypi.org/simple && \
    fix-permissions $CONDA_DIR && \
    fix-permissions /home/$NB_USER && \
    apt update && \
    apt-get install curl -y

ENV PYTHONPATH "${PYTHONPATH}:/home/jovyan/work/q_pack"
    
CMD ["jupyter", "notebook", "--no-browser","--NotebookApp.token=''","--NotebookApp.password=''", "--allow-root"]


# Another way to give root access without password in the compose
    # environment:
    #   GRANT_SUDO: "yes"
    # user:
    #   "root"