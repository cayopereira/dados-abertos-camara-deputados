import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer,
    LineChart, Line
} from 'recharts'
import { obterDeputado, resumoDespesas } from '../services/api'

export default function FichaDeputado() {
    const { id } = useParams()
    const [deputado, setDeputado] = useState(null)
    const [resumo, setResumo] = useState(null)
    const [ano, setAno] = useState(new Date().getFullYear())
    const [erro, setErro] = useState(null)

    useEffect(() => {
        obterDeputado(id)
            .then(setDeputado)
            .catch(() => setErro('Deputado não encontrado.'))
    }, [id])

    useEffect(() => {
        resumoDespesas(id, ano)
            .then(setResumo)
            .catch(() => {})
    }, [id, ano])

    if (erro) return <p role="alert" style={{ padding: '2rem', color: 'red' }}>{erro}</p>
    if (!deputado) return <p aria-live="polite" style={{ padding: '2rem' }}>Carregando...</p>

    return (
        <main style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
            <Link to="/">← Voltar</Link>
            <h1>{deputado.nome}</h1>

            <div style={{ display: 'flex', gap: '1.5rem', alignItems: 'flex-start' }}>
                {deputado.urlFoto && (
                    <img
                        src={deputado.urlFoto}
                        alt={`Foto de ${deputado.nome}`}
                        width={160}
                        style={{ borderRadius: '4px' }}
                    />
                )}
                <dl>
                    <dt><strong>Partido</strong></dt><dd>{deputado.siglaPartido}</dd>
                    <dt><strong>UF</strong></dt><dd>{deputado.siglaUf}</dd>
                    <dt><strong>E-mail</strong></dt><dd>{deputado.email}</dd>
                </dl>
            </div>

            <section aria-labelledby="titulo-despesas" style={{ marginTop: '2rem' }}>
                <h2 id="titulo-despesas">Despesas da Cota Parlamentar</h2>

                <label htmlFor="ano-select">Ano: </label>
                <select
                    id="ano-select"
                    value={ano}
                    onChange={e => setAno(Number(e.target.value))}
                    style={{ padding: '0.25rem', marginLeft: '0.5rem' }}
                >
                    {[2023, 2024, 2025, 2026].map(a => (
                        <option key={a} value={a}>{a}</option>
                    ))}
                </select>

                {resumo?.porTipo?.length > 0 && (
                    <>
                        <h3>Por tipo de despesa</h3>
                        <ResponsiveContainer width="100%" height={400}>
                            <BarChart
                                data={resumo.porTipo.map(item => ({ tipo: item.chave, valor: Number(item.valor) }))}
                                layout="vertical"
                                margin={{ left: 180, right: 30 }}
                            >
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis type="number" />
                                <YAxis dataKey="tipo" type="category" width={170} tick={{ fontSize: 12 }} />
                                <Tooltip formatter={(v) => `R$ ${Number(v).toFixed(2)}`} />
                                <Bar dataKey="valor" fill="#2563eb" />
                            </BarChart>
                        </ResponsiveContainer>
                    </>
                )}

                {resumo?.mensal?.length > 0 && (
                    <>
                        <h3>Evolução mensal</h3>
                        <ResponsiveContainer width="100%" height={300}>
                            <LineChart
                                data={resumo.mensal.map(item => ({ mes: item.chave, valor: Number(item.valor) }))}
                            >
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="mes" />
                                <YAxis />
                                <Tooltip formatter={(v) => `R$ ${Number(v).toFixed(2)}`} />
                                <Line type="monotone" dataKey="valor" stroke="#16a34a" />
                            </LineChart>
                        </ResponsiveContainer>
                    </>
                )}

                {resumo && resumo.porTipo?.length === 0 && (
                    <p>Sem despesas registradas para este ano.</p>
                )}
            </section>
        </main>
    )
}