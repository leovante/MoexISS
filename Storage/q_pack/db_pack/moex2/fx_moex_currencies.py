from db_pack.moex2.DataProviderClient import DataProviderClient

client = DataProviderClient()


def main():
    client.get_securities()


if __name__ == "__main__":
    main()
