import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { createProduct } from '../features/productSlice';

export default function AddProductPage() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [form, setForm] = useState({ name: '', price: '', stock: '' });

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async () => {
        await dispatch(createProduct({
            name: form.name,
            price: parseFloat(form.price),
            stock: parseInt(form.stock),
        }));
        navigate('/products');
    };

    return (
        <div style={{ padding: '1rem' }}>
            <h1>Add Product</h1>
            <input name='name' placeholder='Name' value={form.name} onChange={handleChange} /><br />
            <input name='price' placeholder='Price' value={form.price} onChange={handleChange} /><br />
            <input name='stock' placeholder='Stock' value={form.stock} onChange={handleChange} /><br /><br />
            <button onClick={handleSubmit}>Submit</button>
            <button onClick={() => navigate('/products')} style={{ marginLeft: '1rem' }}>Cancel</button>
        </div>
    );
}
