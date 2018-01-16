#!/usr/bin/python
from time import sleep
import pdb
import requests

if __name__ == "__main__":
    while True:
        #comment out or change as needed.
        auth = ("root","admin")

        #add headers as needed
        headers = {"user-agent":"Bleehu's Repeater"}

        #change the URL as needed
        r = requests.get("http://www.xkcd.com", auth=auth, headers=headers)

        #this prints out the HTTP code and name of the code. ie 200 OK or 404 Not Found
        print "%s, %s" % (r.status_code, r.reason)
        
        #uncomment to see the whole response raw. This can get noisey
        #print r.text

        #uncomment to see the headers of the response
        #print r.headers
        
        sleep(0.05) #sleeping for just a bit makes it so the computer doesn't tire itself out making requests
        
        #Python Debugger (pdb) is a tool that you use in the command line to 
        #look at what variable contain at runtime.
        #from pdb, you can use the dir(variablename) method to list methods and
        #properties of an object.

        #pdb.set_trace()