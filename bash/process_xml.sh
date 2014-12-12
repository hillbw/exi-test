#!/bin/sh

#  ----------
#  Parameters
#  ----------
CLEANUP=1  # Set 1 to remove any files created, 0 to leave them.
# OPENEXI="java -jar /Users/johngalt/Documents/cli/dist/cli.jar" # Alias for the Nagasena OPENEXI command line interface
EXIFICIENT='java -Xms6144M -Xmx6144M -jar ../java/exip/exip.jar' # Alias for the EXIficient command line interface

dir=$1     # 1st parameter: directory path, including final '/'
name=$2    # 2nd parameter: file name w/o extension
ext=$3     # 3rd parameter: file extension, including '.'
output=$4  # 4th parameter: output filename
schema=$5  # 5th parameter: location for corresponding schema, if one exists
echo "Schema read as: ${schema}"

#  ------------------------------------------------------------
#  If a schema is supplied in parameter 5, validate against it.
#  Otherwise, check well-formedness without validation. 
#  Exit if either fails.
#  ------------------------------------------------------------
if [[ -n ${schema} ]]
    then
        echo "Schema (${schema}) supplied. Validating against schema..."
        xmllint --noout --schema ${schema} ${dir}${name}${ext}
else
        echo "No schema supplied. Checking well-formedness..."
        xmllint --noout ${dir}${name}${ext}
fi

if [ $? -ne 0 ]
    then
        echo "XML linting failed. Exiting..."
        exit 1
fi


#  ----------------------
#  Uncompressed Plaintext
#  ----------------------

    #  ----
    #  .xml 
    #  ----
    eval $(stat -s ${dir}${name}${ext})
    echo "${name},xml,$st_size" >> ${output}

#  --------------------
#  Compressed Plaintext
#  --------------------

    #  -------
    #  .xml.gz
    #  -------
    gzip -c ${dir}${name}${ext} > ${dir}${name}${ext}.gz
    eval $(stat -s ${dir}${name}${ext}.gz)
    echo "${name},xml.gz,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.gz
    fi


    #  --------
    #  .xml.bz2
    #  --------
    bzip2 -c ${dir}${name}${ext} > ${dir}${name}${ext}.bz2
    eval $(stat -s ${dir}${name}${ext}.bz2)
    echo "${name},xml.bz2,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bz2
    fi


    #  --------
    #  .xml.zip
    #  --------
    zip -j ${dir}${name}${ext}.zip ${dir}${name}${ext}
    eval $(stat -s ${dir}${name}${ext}.zip)
    echo "${name},xml.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.zip
    fi

#  ---------------------------------
#  Bitpacked EXI Variants, No Schema
#  ---------------------------------

    #  -------------
    #  bitpacked.exi
    #  -------------
    ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-bitpacked.exi -alignment bit_packed
    eval $(stat -s ${dir}${name}-bitpacked.exi)
    echo "${name},xml.bitpacked_exi,$st_size" >> ${output}


    #  ----------------
    #  bitpacked.exi.gz
    #  ----------------
    gzip -c ${dir}${name}-bitpacked.exi > ${dir}${name}-bitpacked.exi.gz
    eval $(stat -s ${dir}${name}-bitpacked.exi.gz)
    echo "${name},xml.bitpacked_exi.gz,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.gz
    fi

    #  -----------------
    #  bitpacked.exi.bz2
    #  -----------------
    bzip2 -c ${dir}${name}-bitpacked.exi > ${dir}${name}-bitpacked.exi.bz2
    eval $(stat -s ${dir}${name}-bitpacked.exi.bz2)
    echo "${name},xml.bitpacked_exi.bz2,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bz2
    fi

    #  -----------------
    #  bitpacked.exi.zip
    #  -----------------
    zip -j ${dir}${name}-bitpacked.exi.zip ${dir}${name}-bitpacked.exi
    eval $(stat -s ${dir}${name}-bitpacked.exi.zip)
    echo "${name},xml.bitpacked_exi.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.exi # done with the bitpacked file, so trash it.
            rm ${dir}*.zip
    fi

#  ---------------------------------
#  Bytealigned EXI Variants, No Schema
#  ---------------------------------

    #  ---------------
    #  bytealigned.exi
    #  ---------------
    ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-bytealigned.exi -alignment byte_packed
    eval $(stat -s ${dir}${name}-bytealigned.exi)
    echo "${name},xml.bytealigned_exi,$st_size" >> ${output}


    #  ------------------
    #  bytealigned.exi.gz
    #  ------------------
    gzip -c ${dir}${name}-bytealigned.exi > ${dir}${name}-bytealigned.exi.gz
    eval $(stat -s ${dir}${name}-bytealigned.exi.gz)
    echo "${name},xml.bytealigned_exi.gz,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.gz
    fi


    #  -------------------
    #  bytealigned.exi.bz2
    #  -------------------
    bzip2 -c ${dir}${name}-bytealigned.exi > ${dir}${name}-bytealigned.exi.bz2
    eval $(stat -s ${dir}${name}-bytealigned.exi.bz2)
    echo "${name},xml.bytealigned_exi.bz2,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.bz2
    fi


    #  -------------------
    #  bytealigned.exi.zip
    #  -------------------
    zip -j ${dir}${name}-bytealigned.exi.zip ${dir}${name}-bytealigned.exi
    eval $(stat -s ${dir}${name}-bytealigned.exi.zip)
    echo "${name},xml.bytealigned_exi.zip,$st_size" >> ${output}

    if [ $CLEANUP -eq 1 ]
        then
            rm ${dir}*.exi
            rm ${dir}*.zip
    fi


#  ---------------
#  precompress.exi
#  ---------------
${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-precompress.exi -alignment pre_compression
eval $(stat -s ${dir}${name}-precompress.exi)
echo "${name},xml.precompress_exi,$st_size" >> ${output}


#  ------------------
#  precompress.exi.gz
#  ------------------
gzip -c ${dir}${name}-precompress.exi > ${dir}${name}-precompress.exi.gz
eval $(stat -s ${dir}${name}-precompress.exi.gz)
echo "${name},xml.precompress_exi.gz,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.gz
fi


#  -------------------
#  precompress.exi.bz2
#  -------------------
bzip2 -c ${dir}${name}-precompress.exi > ${dir}${name}-precompress.exi.bz2
eval $(stat -s ${dir}${name}-precompress.exi.bz2)
echo "${name},xml.precompress_exi.bz2,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.bz2
fi

#  -------------------
#  precompress.exi.zip
#  -------------------  
zip -j ${dir}${name}-precompress.exi.zip ${dir}${name}-precompress.exi
eval $(stat -s ${dir}${name}-precompress.exi.zip)
echo "${name},xml.precompress_exi.zip,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.exi
        rm ${dir}*.zip
fi

#  ------------
#  compress.exi
#  ------------
${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-compress.exi -compression
eval $(stat -s ${dir}${name}-compress.exi)
echo "${name},xml.compress_exi,$st_size" >> ${output}

#  ----------------
#  compress.exi.gz
#  ----------------
gzip -c ${dir}${name}-compress.exi > ${dir}${name}-compress.exi.gz
eval $(stat -s ${dir}${name}-compress.exi.gz)
echo "${name},xml.compress_exi.gz,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.gz
fi

#  ----------------
#  compress.exi.bz2
#  ----------------
bzip2 -c ${dir}${name}-compress.exi > ${dir}${name}-compress.exi.bz2
eval $(stat -s ${dir}${name}-compress.exi.bz2)
echo "${name},xml.compress_exi.bz2,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.bz2
fi

#  ----------------
#  compress.exi.zip
#  ----------------
zip -j ${dir}${name}-compress.exi.zip ${dir}${name}-compress.exi
eval $(stat -s ${dir}${name}-compress.exi.zip)
echo "${name},xml.compress_exi.zip,$st_size" >> ${output}

if [ $CLEANUP -eq 1 ]
    then
        rm ${dir}*.exi
        rm ${dir}*.zip
fi


#  -------------------------------------------------
#  Additional processing for XML files with a schema
#  -------------------------------------------------
if [[ -n ${schema} ]]
    then

    #  --------------
    #  STRICT = FALSE
    #  --------------

        #  --------------------
        #  schema-bitpacked.exi
        #  --------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-schema-bitpacked.exi -alignment bit_packed -schema ${schema}
        eval $(stat -s ${dir}${name}-schema-bitpacked.exi)
        echo "${name},xml.schema_bitpacked_exi,$st_size" >> ${output}


            #  -----------------------
            #  schema-bitpacked.exi.gz
            #  -----------------------
            gzip -c ${dir}${name}-schema-bitpacked.exi > ${dir}${name}-schema-bitpacked.exi.gz
            eval $(stat -s ${dir}${name}-schema-bitpacked.exi.gz)
            echo "${name},xml.schema_bitpacked_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi


            #  ------------------------
            #  schema-bitpacked.exi.bz2
            #  ------------------------
            bzip2 -c ${dir}${name}-schema-bitpacked.exi > ${dir}${name}-schema-bitpacked.exi.bz2
            eval $(stat -s ${dir}${name}-schema-bitpacked.exi.bz2)
            echo "${name},xml.schema_bitpacked_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi


            #  ------------------------
            #  schema-bitpacked.exi.zip
            #  ------------------------
            zip -j ${dir}${name}-schema-bitpacked.exi.zip ${dir}${name}-schema-bitpacked.exi
            eval $(stat -s ${dir}${name}-schema-bitpacked.exi.zip)
            echo "${name},xml.schema_bitpacked_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi


        #  ----------------------
        #  schema-bytealigned.exi
        #  ----------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-schema-bytealigned.exi -alignment byte_packed -schema ${schema}
        eval $(stat -s ${dir}${name}-schema-bytealigned.exi)
        echo "${name},xml.schema_bytealigned_exi,$st_size" >> ${output}


            #  -------------------------
            #  schema-bytealigned.exi.gz
            #  -------------------------
            gzip -c ${dir}${name}-schema-bytealigned.exi > ${dir}${name}-schema-bytealigned.exi.gz
            eval $(stat -s ${dir}${name}-schema-bytealigned.exi.gz)
            echo "${name},xml.schema_bytealigned_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  --------------------------
            #  schema-bytealigned.exi.bz2
            #  --------------------------
            bzip2 -c ${dir}${name}-schema-bytealigned.exi > ${dir}${name}-schema-bytealigned.exi.bz2
            eval $(stat -s ${dir}${name}-schema-bytealigned.exi.bz2)
            echo "${name},xml.schema_bytealigned_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  --------------------------
            #  schema-bytealigned.exi.zip
            #  --------------------------
            zip -j ${dir}${name}-schema-bytealigned.exi.zip ${dir}${name}-schema-bytealigned.exi
            eval $(stat -s ${dir}${name}-schema-bytealigned.exi.zip)
            echo "${name},xml.schema_bytealigned_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi

        #  ----------------------
        #  schema-precompress.exi
        #  ----------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-schema-precompress.exi -alignment pre_compression -schema ${schema}
        eval $(stat -s ${dir}${name}-schema-precompress.exi)
        echo "${name},xml.schema_precompress_exi,$st_size" >> ${output}


            #  -------------------------
            #  schema-precompress.exi.gz
            #  -------------------------
            gzip -c ${dir}${name}-schema-precompress.exi > ${dir}${name}-schema-precompress.exi.gz
            eval $(stat -s ${dir}${name}-schema-precompress.exi.gz)
            echo "${name},xml.schema_precompress_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  --------------------------
            #  schema-precompress.exi.bz2
            #  --------------------------
            bzip2 -c ${dir}${name}-schema-precompress.exi > ${dir}${name}-schema-precompress.exi.bz2
            eval $(stat -s ${dir}${name}-schema-precompress.exi.bz2)
            echo "${name},xml.schema_precompress_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  --------------------------
            #  schema-precompress.exi.zip
            #  --------------------------  
            zip -j ${dir}${name}-schema-precompress.exi.zip ${dir}${name}-schema-precompress.exi
            eval $(stat -s ${dir}${name}-schema-precompress.exi.zip)
            echo "${name},xml.schema_precompress_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi

        #  -------------------
        #  schema-compress.exi
        #  -------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-schema-compress.exi -compression -schema ${schema}
        eval $(stat -s ${dir}${name}-schema-compress.exi)
        echo "${name},xml.schema_compress_exi,$st_size" >> ${output}


            #  -----------------------
            #  schema-compress.exi.gz
            #  -----------------------
            gzip -c ${dir}${name}-schema-compress.exi > ${dir}${name}-schema-compress.exi.gz
            eval $(stat -s ${dir}${name}-schema-compress.exi.gz)
            echo "${name},xml.schema_compress_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  -----------------------
            #  schema-compress.exi.bz2
            #  -----------------------
            bzip2 -c ${dir}${name}-schema-compress.exi > ${dir}${name}-schema-compress.exi.bz2
            eval $(stat -s ${dir}${name}-schema-compress.exi.bz2)
            echo "${name},xml.schema_compress_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  -----------------------
            #  schema-compress.exi.zip
            #  -----------------------
            zip -j ${dir}${name}-schema-compress.exi.zip ${dir}${name}-schema-compress.exi
            eval $(stat -s ${dir}${name}-schema-compress.exi.zip)
            echo "${name},xml.schema_compress_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi

    #  --------------
    #  STRICT = TRUE
    #  --------------

        #  --------------------
        #  schema-bitpacked.exi
        #  --------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-strict-bitpacked.exi -alignment bit_packed -schema ${schema} -strict
        eval $(stat -s ${dir}${name}-strict-bitpacked.exi)
        echo "${name},xml.strict_bitpacked_exi,$st_size" >> ${output}


            #  -----------------------
            #  schema-bitpacked.exi.gz
            #  -----------------------
            gzip -c ${dir}${name}-strict-bitpacked.exi > ${dir}${name}-strict-bitpacked.exi.gz
            eval $(stat -s ${dir}${name}-strict-bitpacked.exi.gz)
            echo "${name},xml.strict_bitpacked_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi


            #  ------------------------
            #  schema-bitpacked.exi.bz2
            #  ------------------------
            bzip2 -c ${dir}${name}-strict-bitpacked.exi > ${dir}${name}-strict-bitpacked.exi.bz2
            eval $(stat -s ${dir}${name}-strict-bitpacked.exi.bz2)
            echo "${name},xml.strict_bitpacked_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi


            #  ------------------------
            #  schema-bitpacked.exi.zip
            #  ------------------------
            zip -j ${dir}${name}-strict-bitpacked.exi.zip ${dir}${name}-strict-bitpacked.exi
            eval $(stat -s ${dir}${name}-strict-bitpacked.exi.zip)
            echo "${name},xml.strict_bitpacked_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi


        #  ----------------------
        #  schema-bytealigned.exi
        #  ----------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-strict-bytealigned.exi -alignment byte_packed -schema ${schema} -strict
        eval $(stat -s ${dir}${name}-strict-bytealigned.exi)
        echo "${name},xml.strict_bytealigned_exi,$st_size" >> ${output}


            #  -------------------------
            #  schema-bytealigned.exi.gz
            #  -------------------------
            gzip -c ${dir}${name}-strict-bytealigned.exi > ${dir}${name}-strict-bytealigned.exi.gz
            eval $(stat -s ${dir}${name}-strict-bytealigned.exi.gz)
            echo "${name},xml.strict_bytealigned_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  --------------------------
            #  schema-bytealigned.exi.bz2
            #  --------------------------
            bzip2 -c ${dir}${name}-strict-bytealigned.exi > ${dir}${name}-strict-bytealigned.exi.bz2
            eval $(stat -s ${dir}${name}-strict-bytealigned.exi.bz2)
            echo "${name},xml.strict_bytealigned_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  --------------------------
            #  schema-bytealigned.exi.zip
            #  --------------------------
            zip -j ${dir}${name}-strict-bytealigned.exi.zip ${dir}${name}-strict-bytealigned.exi
            eval $(stat -s ${dir}${name}-strict-bytealigned.exi.zip)
            echo "${name},xml.strict_bytealigned_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi

        #  ----------------------
        #  schema-precompress.exi
        #  ----------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-strict-precompress.exi -alignment pre_compression -schema ${schema} -strict
        eval $(stat -s ${dir}${name}-strict-precompress.exi)
        echo "${name},xml.strict_precompress_exi,$st_size" >> ${output}


            #  -------------------------
            #  schema-precompress.exi.gz
            #  -------------------------
            gzip -c ${dir}${name}-strict-precompress.exi > ${dir}${name}-strict-precompress.exi.gz
            eval $(stat -s ${dir}${name}-strict-precompress.exi.gz)
            echo "${name},xml.strict_precompress_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  --------------------------
            #  schema-precompress.exi.bz2
            #  --------------------------
            bzip2 -c ${dir}${name}-strict-precompress.exi > ${dir}${name}-strict-precompress.exi.bz2
            eval $(stat -s ${dir}${name}-strict-precompress.exi.bz2)
            echo "${name},xml.strict_precompress_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  --------------------------
            #  schema-precompress.exi.zip
            #  --------------------------  
            zip -j ${dir}${name}-strict-precompress.exi.zip ${dir}${name}-strict-precompress.exi
            eval $(stat -s ${dir}${name}-strict-precompress.exi.zip)
            echo "${name},xml.strict_precompress_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi

        #  -------------------
        #  schema-compress.exi
        #  -------------------
        ${EXIFICIENT} -xml_in ${dir}${name}${ext} -exi_out ${dir}${name}-strict-compress.exi -compression -schema ${schema} -strict
        eval $(stat -s ${dir}${name}-strict-compress.exi)
        echo "${name},xml.strict_compress_exi,$st_size" >> ${output}


            #  -----------------------
            #  schema-compress.exi.gz
            #  -----------------------
            gzip -c ${dir}${name}-strict-compress.exi > ${dir}${name}-strict-compress.exi.gz
            eval $(stat -s ${dir}${name}-strict-compress.exi.gz)
            echo "${name},xml.strict_compress_exi.gz,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.gz
            fi

            #  -----------------------
            #  schema-compress.exi.bz2
            #  -----------------------
            bzip2 -c ${dir}${name}-strict-compress.exi > ${dir}${name}-strict-compress.exi.bz2
            eval $(stat -s ${dir}${name}-strict-compress.exi.bz2)
            echo "${name},xml.strict_compress_exi.bz2,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.bz2
            fi

            #  -----------------------
            #  schema-compress.exi.zip
            #  -----------------------
            zip -j ${dir}${name}-strict-compress.exi.zip ${dir}${name}-strict-compress.exi
            eval $(stat -s ${dir}${name}-strict-compress.exi.zip)
            echo "${name},xml.strict_compress_exi.zip,$st_size" >> ${output}

            if [ $CLEANUP -eq 1 ]
                then
                    rm ${dir}*.exi
                    rm ${dir}*.zip
            fi
fi
