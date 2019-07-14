while true
do
    temp=$(shuf -i 18-53 -n 1)
    number=$(shuf -i 1-3113 -n 1)

    curl -v -s -S X POST http://localhost:9001 \
    --header 'Content-Type: application/json; charset=utf-8' \
    --header 'Accept: application/json' \
    --header 'User-Agent: orion/0.10.0' \
    --header "Fiware-Service: demo" \
    --header "Fiware-ServicePath: /test" \
    -d  '{
         "data": [
             {
                 "id": "R1","type": "Node",
                 "co": {"type": "Float","value": 0,"metadata": {}},
                 "co2": {"type": "Float","value": 0,"metadata": {}},
                 "humidity": {"type": "Float","value": 40,"metadata": {}},
                 "pressure": {"type": "Float","value": '$number',"metadata": {}},
                 "temperature": {"type": "Float","value": '$temp',"metadata": {}},
                 "wind_speed": {"type": "Float","value": 1.06,"metadata": {}}
             }
         ],
         "subscriptionId": "57458eb60962ef754e7c0998"
     }'
	echo $temp

    sleep 1
done