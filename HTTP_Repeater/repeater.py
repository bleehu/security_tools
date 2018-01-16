#!/usr/bin/python
import pdb
import requests

if __name__ == "__main__":
    while True:
        r = requests.get("https://www.xkcd.com")
        print r.status_code
        pdb.set_trace()