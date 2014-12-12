totallines=30

for i in $(seq 10 10 ${totallines})
    do
        json_file="owm-1-${i}cities.json"
        echo "building ${json_file} ..."
        if [ -f ${json_file} ]
            then 
                rm ${json_file} 
        fi
        touch ${json_file}
        next_to_last_line=$(( ${i} - 1 ))
        
        # Breakdown in order of commands:
        # use head to get the first i lines of the master file
        # pipe it to sed
        # sed commands, in order:
        # -replace the start of the first line with the JSON array beginning
        # -replace the EOL character for all lines except the last with a comma
        # -at the end of the last line, add the close of the JSON array
        # redirect the sed output to the final file
        head -${i} ../owm-1-master.json | sed -e "1 s/^/{ \"cnt\": ${i}, \"list\": [ /" -e "1,${next_to_last_line} s/$/,/" -e "${i} s/$/ ] }/" >> ${json_file}  

done
