import { useDispatch, useSelector } from 'react-redux';
import { fetchPaginatedProducts } from '../features/productSlice';
import { useState, useEffect } from 'react';

function PaginatedProducts() {
    const dispatch = useDispatch();
    const { paginated } = useSelector((state) => state.products);
    const [page, setPage] = useState(0);

    useEffect(() => {
        dispatch(fetchPaginatedProducts({ page, size: 5, sortBy: 'id', direction: 'asc' }));
    }, [dispatch, page]);

    if (!paginated) return null;

    return (
        <div>
            <h3>Paginated Products (Page {page + 1} of {paginated.totalPages})</h3>
            <table border='1'>
                <thead><tr><th>ID</th><th>Name</th><th>Price</th><th>Stock</th></tr></thead>
                <tbody>
                    {paginated.content.map((p) => (
                        <tr key={p.id}>
                            <td>{p.id}</td><td>{p.name}</td>
                            <td>${p.price}</td><td>{p.stock}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button disabled={page === 0} onClick={() => setPage(page - 1)}>← Prev</button>
            <button disabled={page >= paginated.totalPages - 1}
                onClick={() => setPage(page + 1)}>Next →</button>
        </div>
    );
}
