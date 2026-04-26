import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';
import Spinner from '../components/Spinner';
import ErrorMessage from '../components/ErrorMessage';

export default function ProductListPage() {
    const [search, setSearch] = useState('');
    const [minPrice, setMinPrice] = useState(0);
    const { products, loading, error } = useProducts(search, minPrice);
    const { addToCart } = useCart(1); // using cartId=1 for demo
    const navigate = useNavigate();

    if (loading) return <Spinner />;
    if (error) return <ErrorMessage message={error} />;

    return (
        <div style={{ padding: '1rem' }}>
            <h1>Products</h1>
            <button onClick={() => navigate('/add-product')}>+ Add Product</button>
            <button onClick={() => navigate('/cart')} style={{ marginLeft: '1rem' }}>🛒 View Cart</button>
            <br /><br />
            <input placeholder='Search...' value={search}
                onChange={(e) => setSearch(e.target.value)} />
            <input type='number' placeholder='Min Price' value={minPrice}
                onChange={(e) => setMinPrice(Number(e.target.value))}
                style={{ marginLeft: '1rem' }} />
            <table border='1' style={{ width: '100%', marginTop: '1rem' }}>
                <thead>
                    <tr><th>ID</th><th>Name</th><th>Price</th><th>Stock</th><th>Action</th></tr>
                </thead>
                <tbody>
                    {products.map((p) => (
                        <tr key={p.id}>
                            <td>{p.id}</td>
                            <td>{p.name}</td>
                            <td>${p.price}</td>
                            <td>{p.stock}</td>
                            <td>
                                <button onClick={() => addToCart(p.id, 1)}>Add to Cart</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
