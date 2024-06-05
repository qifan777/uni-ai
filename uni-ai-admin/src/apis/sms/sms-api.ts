import {request} from '@/utils/request'

export const sendSMS = (phone: string) => {
  return request<boolean>({ url: '/sms/send?phone=' + phone, method: 'post' })
}
