import { api } from './client'
import { tokenStorage } from '../lib/tokenStorage'

function extractBearer(res, data) {
  const fromHeader = res.headers.get('Authorization')
  if (fromHeader) return fromHeader
  const maybe = data?.result
  if (typeof maybe === 'string' && maybe.startsWith('Bearer ')) return maybe
  return null
}

async function join({ loginId, password }) {
  const { res, data } = await api.request('/api/member/join', {
    method: 'POST',
    body: { loginId, password },
  })
  const bearer = extractBearer(res, data)
  if (!bearer) throw new Error('토큰이 응답에 없습니다.')
  tokenStorage.set(bearer)
  return bearer
}

async function login({ loginId, password }) {
  const { res, data } = await api.request('/api/member/login', {
    method: 'POST',
    body: { loginId, password },
  })
  const bearer = extractBearer(res, data)
  if (!bearer) throw new Error('토큰이 응답에 없습니다.')
  tokenStorage.set(bearer)
  return bearer
}

async function me() {
  const token = tokenStorage.get()
  if (!token) throw new Error('로그인이 필요합니다')
  const { data } = await api.request('/api/member/me', {
    method: 'GET',
    token,
  })
  return data.result
}

export const auth = { join, login, me }

