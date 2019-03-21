curl -v localhost:1026/v2/subscriptions -s -S -H 'Content-Type: application/json' -d @- <<EOF
{
  "description": "A subscription to get info about Room1",
  "subject": {
	"entities": [
  	{
    	"id": "Room1",
    	"type": "Room"
  	}
	],
	"condition": {
  	"attrs": [
    	"pressure",
	"temperature"
  	]
	}
  },
  "notification": {
	"http": {
  	"url": "http://taskmanager:9001/notify"
	},
	"attrs": [
  	"temperature",
	"pressure"
	]
  },
  "expires": "2040-01-01T14:00:00.00Z",
  "throttling": 5
}
EOF

