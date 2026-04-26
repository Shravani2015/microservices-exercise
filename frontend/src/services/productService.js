import axios from 'axios';

const BASE_URL = 'http://localhost:8081/api/products';

export const productService = {
    getAll: () => axios.get(BASE_URL),
    getById: (id) => axios.get(`${BASE_URL}/${id}`),
    create: (data) => axios.post(BASE_URL, data),
    update: (id, data) => axios.put(`${BASE_URL}/${id}`, data),
    delete: (id) => axios.delete(`${BASE_URL}/${id}`),
    getPaginated: (page, size, sortBy, direction) =>
        axios.get(`${BASE_URL}/paginated?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`),
    search: (name) => axios.get(`${BASE_URL}/search?name=${name}`),
};
