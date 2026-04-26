import { useEffect } from 'react';
import { useCart } from '../hooks/useCart';
import Spinner from '../components/Spinner';
import ErrorMessage from '../components/ErrorMessage';

export default function CartPage() {
    const { items, loading, error, loadItems } = useCart(1);

    useEffect(() => {
        loadItems();
    }, [loadItems]);

    if (loading) return <Spinner />;
    if (error) return <ErrorMessage message={error} />;

    return (
        <div style={{ padding: '1rem' }}>
            <h1>🛒 Cart</h1>
            {items.length === 0 ? <p>Cart is empty.</p> : (
                <table border='1'>
                    <thead>
                        <tr><th>Item ID</th><th>Product ID</th><th>Quantity</th></tr>
                    </thead>
                    <tbody>
                        {items.map((item) => (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.productId}</td>
                                <td>{item.quantity}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
