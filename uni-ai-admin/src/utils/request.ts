import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

const BASE_URL = import.meta.env.VITE_API_PREFIX
export const request = axios.create({
  baseURL: BASE_URL,
  timeout: 10000
})
request.interceptors.response.use(
  (res) => {
    return res.data.result
  },
  ({ response }) => {
    if (response.data.code !== 1) {
      ElMessage.warning({ message: response.data.msg })
    }
    if (response.data.code === 1001010) {
      router.push('/rest-password')
    }
    if (response.data.code === 1001007 || response.data.code === 1001008) {
      console.log(1001007)
      router.push('/login')
    } else {
      /* empty */
    }
    return Promise.reject(response.data.result)
  }
)
