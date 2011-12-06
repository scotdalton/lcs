#!/bin/bash
home_dir=~

longitudeFile=${home_dir}/longitude
touch $longitudeFile
#if [[ -f $longitudeFile ]]; then
  echo 27 > $longitudeFile
#fi
longitudeStart=$(cat $longitudeFile)
longitudeEnd=$(expr $longitudeStart - 1)
echo $longitudeEnd > $longitudeFile

latitudeFile=${home_dir}/latitude
touch $latitudeFile
#if [[ -f $latitudeFile ]]; then
  echo 31 > $latitudeFile
#fi
latitudeStart=$(cat $latitudeFile)
latitudeEnd=$(expr $latitudeStart - 9)
echo $latitudeEnd > $latitudeFile

java -Xms256m -Xmx1024m -cp \
  "${home_dir}/Dropbox/MSIS/SystemsProjects/google_earth/lib/guava-10.0.1.jar:${home_dir}/Dropbox/MSIS/SystemsProjects/google_earth/bin/" \
  edu.nyu.cs.sysproj.arability.utility.ImageGrabber \
  "longitudeStart=-0.0603390" \
  "longitudeEnd=-0.29911990" \
  "latitudeStart=5.668430499999999" \
  "latitudeEnd=5.5139870"
  # "longitudeStart=${longitudeStart}" \
  # "longitudeEnd=${longitudeEnd}" \
  # "latitudeStart=${latitudeStart}" \
  # "latitudeEnd=${latitudeEnd}"
