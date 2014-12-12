for i in $(seq 1 1 9)
    do
        echo i is now ${i}
        xsltproc --output ../cases/small/gpx/xml/gpx-1-${i}pts.gpx --param i ${i} resize_gpx.xsl ../cases/small/gpx/gpx-1-master.gpx
done


for i in $(seq 10 10 1000)
    do
        echo i is now ${i}
        xsltproc --output ../cases/small/gpx/xml/gpx-1-${i}pts.gpx --param i ${i} resize_gpx.xsl ../cases/small/gpx/gpx-1-master.gpx
done


