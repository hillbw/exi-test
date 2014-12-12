import sys
import json
import cbor

# Get filename base (without extension) from argument list
fname = sys.argv[1]

# Open the JSON version of the file, and a new file
# to hold the CBOR version.
fin = open(fname + '.json', 'r')
fout = open(fname + '.cbor', 'wb')

# Parse the JSON string from the file into 
# a Python dictionary object
data = json.load(fin)

# Use CBOR codec to write to
cbor.dump(data, fout)

# Close both files
fin.close()
fout.close()