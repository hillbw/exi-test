echo "Building owm-1-1000cities.xml"
xml_file="../cases/small/owm/owm-1-1000cities.xml"
rm ${xml_file}
touch ${xml_file}

for j in $(seq 1 1 1000)
    do

        id=`sed "${j}q;d" city_ids.txt` # get the j-th lines from list of city IDs
        echo "Iteration ${j} - Getting CityID ${id}"
        # Get current weather in XML
        curl "api.openweathermap.org/data/2.5/weather?id=${id}&mode=xml&APPID=cc2de11073c36c4c6069d5b642956c3d" >> ${xml_file}
done

# Clean up the newly made XML file
sed -e '/^\<\?xml.*$/ d' ${xml_file} > /tmp/out && mv /tmp/out ${xml_file} # Drop all XML declarations
echo "<group>"|cat - ${xml_file} > /tmp/out && mv /tmp/out ${xml_file} # Prepend contrived <group> root element
echo "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"|cat - ${xml_file} > /tmp/out && mv /tmp/out ${xml_file} # Re-add a single XML declaration
echo "</group>" >> ${xml_file} # Append closing </group> tag
sed -e '/^$/ d' ${xml_file} > /tmp/out && mv /tmp/out ${xml_file} # Drop empty lines


