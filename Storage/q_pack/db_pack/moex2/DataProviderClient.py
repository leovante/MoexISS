import logging

import q_credentials.api_moex as api_moex
import requests
from fastapi import HTTPException


class DataProviderClient:
    def __init__(self):
        self.logger = logging.getLogger(self.__class__.__name__)
        self.pre_host = "http://"
        self.host = api_moex.host
        self.port = api_moex.port
        self.uri = api_moex.get_securities_uri

    def get_securities(self):
        url = f"{self.pre_host}{self.host}:{self.port}{self.uri}"
        self.logger.info(f"fetching securities moex by url {url}")
        try:
            res = requests.get(url)
            self.logger.info(f"response is {res}")
            res_json = res.json()
            for item in res_json:
                if (len(item) > 0):
                    print(item[1])
        except Exception as exp:
            self.logger.error("error fetching securities", exp)
            raise HTTPException(status_code=500, detail="error fetching securities from dataprovider")
        self.logger.info(f"fetched {res_json.__len__():d} securities")
