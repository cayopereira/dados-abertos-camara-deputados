import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { listarDeputados } from '../services/api'

export default function ListaDeputados() {
    const [deputados, setDeputados] = useState([])
    const [nome, setNome] = useState('')
    const [loading, setLoading] = useState(false)
    const [erro, setErro] = useState(null)

    useEffect(() => {
        carregar()
    }, [])

    async function carregar() {
        setLoading(true)
        setErro(null)
        try {
            const data = await listarDeputados({ nome, size: 20 })
            setDeputados(data.content || [])
        } catch (e) {
            setErro('Não foi possível carregar os deputados. Verifique se o backend está rodando.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <main style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
            <h1>Deputados Federais</h1>

            <form
                role="search"
                onSubmit={(e) => { e.preventDefault(); carregar() }}
                style={{ marginBottom: '1.5rem' }}
            >
                <label htmlFor="busca" style={{ display: 'block', marginBottom: '0.5rem' }}>
                    Buscar por nome
                </label>
                <input
                    id="busca"
                    type="search"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    aria-label="Buscar deputado por nome"
                    style={{ padding: '0.5rem', marginRight: '0.5rem', width: '300px' }}
                />
                <button type="submit" style={{ padding: '0.5rem 1rem' }}>Buscar</button>
            </form>

            {loading && <p aria-live="polite">Carregando...</p>}
            {erro && <p role="alert" style={{ color: 'red' }}>{erro}</p>}

            <ul aria-label="Lista de deputados" style={{ listStyle: 'none', padding: 0 }}>
                {deputados.map(dep => (
                    <li key={dep.id} style={{ display: 'flex', gap: '1rem', marginBottom: '1rem', alignItems: 'center' }}>
                        {dep.urlFoto && (
                            <img src={dep.urlFoto} alt="" width={48} height={64} style={{ objectFit: 'cover' }} />
                        )}
                        <Link to={`/deputados/${dep.id}`}>
                            {dep.nome} — {dep.siglaPartido}/{dep.siglaUf}
                        </Link>
                    </li>
                ))}
            </ul>

            {!loading && deputados.length === 0 && (
                <p>Nenhum deputado encontrado.</p>
            )}
        </main>
    )
}