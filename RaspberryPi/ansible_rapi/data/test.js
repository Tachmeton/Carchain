import * as FormData from 'form-data';
import { request } from 'http';
import { createReadStream } from 'fs';
 
const readStream = createReadStream('./big-bobby-car-classic-800001303_00.jpeg');
 
const form = new FormData();
form.append('carid', 'Schtring1');
form.append('carimage', readStream);
 
const req = request(
  {
    host: '193.196.54.51',
    port: '3000',
    path: '/addImage',
    method: 'PUT',
    headers: form.getHeaders(),
  },
  response => {
    console.log(response.statusCode); // 200
  }
);
 
form.pipe(req);
