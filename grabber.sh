#!/bin/bash
home_dir=~

longitudeFile=${home_dir}/longitude
if [[ -f $longitudeFile ]]; then
  echo 35 > $longitudeFile
fi
longitudeStart=$(cat $longitudeFile)
longitudeEnd=$(expr $longitudeStart + 2)
echo $longitudeEnd > $longitudeFile

latitudeFile=${home_dir}/latitude
if [[ -f $latitudeFile ]]; then
  echo 4 > $latitudeFile
fi
latitudeStart=$(cat $latitudeFile)
latitudeEnd=$(expr $latitudeStart - 9)
echo $latitudeEnd > $latitudeFile

java -Xms256m -Xmx1024m -cp \
  "${home_dir}/Dropbox/MSIS/SystemsProjects/google_earth/lib/guava-10.0.1.jar:${home_dir}/Dropbox/MSIS/SystemsProjects/google_earth/bin/" \
  edu.nyu.cs.sysproj.arability.utility.ImageGrabber \
  "longitudeStart=${longitudeStart}" \
  "longitudeEnd=${longitudeEnd}" \
  "latitudeStart=${latitudeStart}" \
  "latitudeEnd=${latitudeEnd}"
