# input JSON
 
```JSON
{
  "availableTags": [
    {
      "tag": "pool",
      "values": [
        "HikariPool-1"
      ]
    }
  ],
  "description": "Pending threads",
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 0.0
    }
  ],
  "name": "hikaricp.connections.pending"
}
```

## 결과
- connections pools 대기: 8 -> 6 -> 4 -> 2 -> 0
