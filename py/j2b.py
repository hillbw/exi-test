import sys
import json
import bson

fname = sys.argv[1]
json_data = open(fname + '.json')
#print('j2b.py: reading from ' + fname + '.json')

data = json.load(json_data)
bson_data = bson.serialize_to_bytes(data) # returns bytes
json_data.close()
f = open(fname + '.bson', 'wb')

#print('j2b.py: writing to ' + fname + '.bson')
f.write(bson_data)
