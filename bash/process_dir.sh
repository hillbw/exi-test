#!/bin/sh

#  -----------------------------------------------
#  Parameters controlling input and output sources
#  -----------------------------------------------

# sample_dir="../../../chopblock"  # flat directory containing XML/JSON files to be compressed
sample_dir="/Volumes/ExternalHDD/cases/big/osm/xml"
working_dir="./temp/"   # a temp directory for working on files - needs trailing '/'
output_file="../data/gpx_results.csv"   # where to write the test results?


#  --------------------------------------------------
#  Erase any existing output file and start a new one
#  --------------------------------------------------
rm ${output_file}
touch ${output_file}
echo "file,variable,value" >> ${output_file}


#  --------------------------------------------------
#  Create temporary directory. Before processing, all 
#  files are moved into this directory first. Useful 
#  to safeguard original files, and as a staging area
#  for moving large files off an external HDD.
#  --------------------------------------------------
rm -r ${working_dir}
mkdir ${working_dir}


#  ---------------------
#  Iterate through files
#  ---------------------
for f in `find ${sample_dir} -type f \( -name "*.rss" -or -name "*.xml" -or -name "*.json" -or -name "*.gpx" -or -name "*.osm" -or -name "*.dfxml" -or -name "*.pdml" -or -name "*.ais" -or -name "*.owm" \)`
do
    
    echo "Processing ${f} ..."
    cp ${f} ${working_dir}  # Move them one-by-one into the temp directory. Good for use with big files on external drive
    path=${f%/*.*}/         # Break apart the current file location into path,filename, and extension
    base=$(basename ${f})
    extension=.${base##*.}
    filename=${base%.*}

    # Choose the appropriate processing script based on the extension
    case ${extension} in
        .gpx)
            schema="../cases/small/gpx/xsd/gpx.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema="" # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .dfxml)
            schema="../cases/large/dfxml/xsd/dfxml.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema="" # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .pdml)
            schema="../cases/large/pdml/xsd/pdml.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema="" # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .osm)
            schema="../cases/large/osm/xsd/osm.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema="" # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .ais)
            schema="../cases/small/ais/xsd/ais1.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema="" # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .owm)
            schema="../cases/small/owm/xsd/owm.xsd"
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ${schema}
            schema=""  # Null out schema to prevent confusion in possible mixed content runs
            ;;
        .xml|.rss)
            ./process_xml.sh ${working_dir} ${filename} ${extension} ${output_file} ""
            ;;
        .json )
            ./process_json.sh ${working_dir} ${filename} ${extension} ${output_file}
            ;;
    esac    
done


#  ------------------------------------------------------
#  Trash the working directory and dump results to stdout
#  ------------------------------------------------------
rm -r ${working_dir}

echo "RESULTS SUMMARY"
echo "---------------"
cat ${output_file}
