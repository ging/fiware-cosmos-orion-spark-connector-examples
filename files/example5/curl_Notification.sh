while true
do
    bus1=$(shuf -i 10-53 -n 1)
    bus2=$(shuf -i 10-44 -n 1)

    curl -v -s -S X POST http://localhost:9001 \
    --header 'Content-Type: application/json; charset=utf-8' \
    --header 'Accept: application/json' \
    --header 'User-Agent: orion/0.10.0' \
    --header "Fiware-Service: demo" \
    --header "Fiware-ServicePath: /test" \
    -d  '{
         "data": [
             {
                 "id": "R1",
                 "type": "Node",
                 "information": {
                     "type": "object",
                     "value": {
                        "buses":[
                            {
                                "name": "BusCompany1",
                                "schedule": {
                                    "morning": [7,9,11],
                                    "afternoon": [13,15,17,19],
                                    "night" : [23,1,5]
                                },
                                "price": '$bus1'
                            },
                            {
                                "name": "BusCompany2",
                                "schedule": {
                                    "morning": [8,10,12],
                                    "afternoon": [16,20],
                                    "night" : [23]
                                },
                                "price": '$bus2'
                            }
                        ]
                     },
                     "metadata": {}
                    }
             }
         ],
         "subscriptionId": "57458eb60962ef754e7c0998"
     }'


    sleep 1
done