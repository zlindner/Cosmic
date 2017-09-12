for x do
	echo Making $x.json
	touch ~/.$x.json
	echo "{" >> $x.json
	echo "    \"parent\": \"item/generated\"," >> $x.json
	echo "    \"textures\": {"  >> $x.json
	echo "        \"layer0\": \"cosmic:items/$x" >> $x.json
	echo "    }" >> $x.json
	echo "}" >> $x.json
done