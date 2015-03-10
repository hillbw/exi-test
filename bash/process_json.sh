#!/bin/sh

# ------------------------------------------------
# Perform a variety of encodings for a single JSON
# file and write the resulting file sizes to a CSV
# file.
# ------------------------------------------------

    #  --------------------
    #  Parameters and Setup
    #  --------------------
    CLEANUP=1 # Set 1 to remove any files created, 0 to leave them.

    dir=$1    # 1st parameter: directory path, including final '/'
    name=$2   # 2nd parameter: file name w/o extension
    ext=$3    # 3rd parameter: file extension, including '.'
    output=$4 # 4th parameter: output filename


#  ----------------------
#  Uncompressed Plaintext
#  ----------------------

    #  -----
    #  .json 
    #  -----
    eval $(stat -s ${dir}${name}${ext})
    base_size=$st_size
    echo "${name},json,$st_size" >> ${output}


#  --------------------
#  Compressed Plaintext
#  --------------------

    #  --------
    #  .json.gz
    #  --------
    gzip ${dir}${name}${ext}
    eval $(stat -s ${dir}${name}${ext}.gz)
    echo "${name},json.gz,$st_size" >> ${output}
    gunzip ${dir}${name}${ext}


    #  ---------
    #  .json.bz2
    #  ---------
    bzip2 ${dir}${name}${ext}
    eval $(stat -s ${dir}${name}${ext}.bz2)
    echo "${name},json.bz2,$st_size" >> ${output}
    bunzip2 ${dir}${name}${ext}.bz2


    #  ---------
    #  .json.zip
    #  ---------
    zip -j ${dir}${name}${ext}.zip ${dir}${name}${ext}
    eval $(stat -s ${dir}${name}${ext}.zip)
    echo "${name},json.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.zip
    fi

#  -------------------
#  Uncompressed Binary
#  -------------------

    #  -----
    #  .bson
    #  -----
    python3.4 ../py/j2b.py ${dir}${name}
    eval $(stat -s ${dir}${name}.bson)
    echo "${name},json.bson,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bson
    fi


    #  -----
    #  .cbor
    #  -----
    python3.4 ../py/j2c.py ${dir}${name}
    eval $(stat -s ${dir}${name}.cbor)
    echo "${name},json.cbor,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.cbor
    fi


#  -------------------
#  Compressed Binary
#  -------------------

    #  --------
    #  .bson.gz
    #  --------
    python3.4 ../py/j2b.py ${dir}${name}
    gzip ${dir}${name}.bson
    eval $(stat -s ${dir}${name}.bson.gz)
    echo "${name},json.bson.gz,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.gz
    fi


    #  ---------
    #  .bson.bz2
    #  ---------
    python3.4 ../py/j2b.py ${dir}${name}
    bzip2 ${dir}${name}.bson
    eval $(stat -s ${dir}${name}.bson.bz2)
    echo "${name},json.bson.bz2,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bz2
    fi


    #  --------
    #  bson.zip
    #  --------
    python3.4 ../py/j2b.py ${dir}${name}
    zip -j ${dir}${name}.bson.zip ${dir}${name}.bson
    eval $(stat -s ${dir}${name}.bson.zip)
    echo "${name},json.bson.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.zip
            rm ${dir}*.bson
    fi


    #  --------
    #  .cbor.gz
    #  --------
    python3.4 ../py/j2c.py ${dir}${name}
    gzip ${dir}${name}.cbor
    eval $(stat -s ${dir}${name}.cbor.gz)
    echo "${name},json.cbor.gz,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.gz
    fi


    #  ---------
    #  .cbor.bz2
    #  ---------
    python3.4 ../py/j2c.py ${dir}${name}
    bzip2 ${dir}${name}.cbor
    eval $(stat -s ${dir}${name}.cbor.bz2)
    echo "${name},json.cbor.bz2,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bz2
    fi


    #  --------
    #  .cbor.zip
    #  --------
    python3.4 ../py/j2c.py ${dir}${name}
    zip -j ${dir}${name}.cbor.zip ${dir}${name}.cbor
    eval $(stat -s ${dir}${name}.cbor.zip)
    echo "${name},json.cbor.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.zip
            rm ${dir}*.cbor
    fi
