#!/bin/bash

for i in $*
do
	echo "input $i"
done

for j in $@
do
	echo "input $j"
done
