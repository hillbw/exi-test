echo "building ../cases/small/owm/json/owm-1-master.json"
json_file="../cases/small/owm/json/owm-1-master.json"
rm ${json_file}
touch ${json_file}

for j in $(seq 1 1 1000)
    do
        
        id=`sed "${j}q;d" city_ids.txt` # get the j-th lines from list of city IDs, 

        echo "Iteration: ${j} City ID: ${id}"
        
        # Get current weather in JSON
        curl "http://api.openweathermap.org/data/2.5/weather?id=${id}&APPID=cc2de11073c36c4c6069d5b642956c3d" >> ${json_file}
done


