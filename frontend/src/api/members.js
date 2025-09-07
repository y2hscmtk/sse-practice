import { api } from './client'
import { tokenStorage } from '../lib/tokenStorage'

function toQuery(params) {
  const usp = new URLSearchParams()
  Object.entries(params || {}).forEach(([k, v]) => {
    if (v === undefined || v === null || v === '') return
    usp.set(k, String(v))
  })
  const s = usp.toString()
  return s ? `?${s}` : ''
}

async function list({ page = 1, size = 10 } = {}) {
  const token = tokenStorage.get()
  const { data } = await api.request(`/api/member/list${toQuery({ page, size })}`, {
    method: 'GET',
    token,
  })
  // result is an array of { loginId, authority }
  return data.result || []
}

export const members = { list }

