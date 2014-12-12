# --------------------------------------
# Batch remove whitespace from XML files
# --------------------------------------

# Takes 3 parameters:
#    $1: folder containing xml files to be processed
#    $2: expected filename extension (no .)
#    $3: relative path to schema

for f in `find ${1} -type f -name "*.${2}"`
do
    
    echo "Stripping whitespace from ${f} ..."
    xmllint -noblanks ${f} > temp.${2}
    mv temp.${2} ${f}
    xmllint -noout -schema ${3} ${f} 
    
    if [ $? -ne 0 ]
    then
        echo "XML linting failed. Exiting..."
        exit 1
    fi

done