#!/bin/bash

for i in "$*"
#$*中的所有参数看成是一个整体，所以for循环只会循环一次
	do
		echo "$i"
	done
for j in "$@"
#$@中的每个参数都看成独立的，所以“$@”中有几个参数，就会循环几次
	do
		echo "$j"
	done
