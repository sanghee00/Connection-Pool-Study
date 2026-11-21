import http from 'k6/http'
import { check } from 'k6';

export let options = {
    vus: 10,
    duration: '30s'
}

const URL = 'http://127.0.0.1:8080/api/sleep';

export default function () {
    const res = http.get(URL);
    console.log(res.status);
    check(res, {'status 200': (r) => r.status === 200});
};
