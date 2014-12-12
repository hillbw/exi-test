import sys
import json
import bson
import cbor


# Get filename base (without extension) from argument list
fname = sys.argv[1]

# Open the JSON version of the file, and a new file
# to hold the BSON version.
fin = open(fname + '.json', 'r')
fout = open(fname + '.bson', 'wb')

# Parse the JSON string from the file into 
# a Python dictionary object
data = json.load(fin)

# Write the object to the file in BSON
fout.write(bson.serialize_to_bytes(data))

# Close both files
fin.close()
fout.close()

# Open a new file for writing out the CBOR encoding
fout = open(fname + '.cbor', 'wb')

# Use CBOR codec to write to
cbor.dump(data, fout)

# Close the CBOR file
fout.close()


# Open the BSON version in read-only mode, and a new file
# for the roundtrip JSON output.
fin = open(fname + '.bson', 'rb')
fout = open(fname + '-roundtrip.json', 'w')

# Parse the BSON file into a Python dictionary object
data = bson.parse_stream(fin)

# Dump the dictionary object out in JSON format
json.dump(data, fout)

# Close both files
fin.close()
fout.close()

# #print('j2b.py: writing to ' + fname + '.bson')

# f.close()
# f = open(fname + '.bs')

# f2 = open(fname + '-roundtrip.bson', 'w')
# parsed_from_bson = bson.parse_stream(f2)
# 