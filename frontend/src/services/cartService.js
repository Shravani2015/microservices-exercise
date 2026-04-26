import axios from 'axios';

const BASE_URL = 'http://localhost:8082/api/carts';

export const cartService = {
    createCart: (userId) => axios.post(`${BASE_URL}?userId=${userId}`),
    getCart: (cartId) => axios.get(`${BASE_URL}/${cartId}`),
    addItem: (cartId, data) => axios.post(`${BASE_URL}/${cartId}/items`, data),
    getItems: (cartId) => axios.get(`${BASE_URL}/${cartId}/items`),
    removeItem: (itemId) => axios.delete(`${BASE_URL}/items/${itemId}`),
};
