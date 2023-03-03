while true
do
    temp=$(shuf -i 18-53 -n 1)
    number=$(shuf -i 1-3113 -n 1)

    curl -v -s -S -X POST http://localhost:9002 \
    --header 'Content-Type: application/json; charset=utf-8' \
    --header 'Accept: application/json' \
    -d  '{
            "id": "urn:ngsi-ld:Notification:5fd0fa684eb81930c97005f3",
            "type": "Notification",
            "subscriptionId": "urn:ngsi-ld:Subscription:5fd0f69b4eb81930c97005db",
            "notifiedAt": "2023-2-23T16:25:12.193Z",
             "data": [
             {
                 "id": {"type": "Property","value": "R1"},
                 "co": {"type": "Property","value": 0},
                 "co2": {"type": "Property","value": 0},
                 "humidity": {"type": "Property","value": 40},
                 "pressure": {"type": "Property","value": '$number'},
                 "temperature": {"type": "Property","value": '$temp'},
                 "wind_speed": {"type": "Property","value": 1.06}
             }
         ]
        }'
	echo $temp

    sleep 1
done