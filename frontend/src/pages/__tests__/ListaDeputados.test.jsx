import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import ListaDeputados from '../ListaDeputados'
import { describe, it, expect, vi } from 'vitest'

// Mockamos a chamada de API para o teste não depender do backend rodando
vi.mock('../../services/api', () => ({
    listarDeputados: vi.fn().mockResolvedValue({ content: [] })
}))

describe('ListaDeputados Component', () => {
    it('deve renderizar o título principal da página', () => {
        render(
            <MemoryRouter>
                <ListaDeputados />
            </MemoryRouter>
        )
        // Verifica se o h1 com o texto "Deputados Federais" está na tela
        expect(screen.getByRole('heading', { name: /Deputados Federais/i })).toBeInTheDocument()
    })
})