curl localhost:1026/v2/entities/Room1/attrs -s -S -H 'Content-Type: application/json' -X PUT -d @- <<EOF
{
  "temperature": {
    "value": 26.5,
    "type": "Float"
  },
  "temperature_min": {
    "value": 0,
    "type": "Float"
  },
  "pressure": {
    "value": 763,
    "type": "Float"
  }
}
EOF

curl localhost:1026/v2/entities
