for i in $(seq 1 1 9)
    do
        echo "Param i = ${i}"
        xsltproc --output ../cases/small/owm/xml/owm-1-${i}cities.xml --param i ${i} resize_owm.xsl ../cases/small/owm/owm-1-master.xml
done


# for i in $(seq 530 10 530)
#     do
#         echo "Param i = ${i}"
#         xsltproc --output ../cases/small/owm/xml/owm-1-${i}cities.xml --param i ${i} resize_owm.xsl ../cases/small/owm/owm-1-master.xml
# done


