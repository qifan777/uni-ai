import {ElMessage} from 'element-plus'
import _ from 'lodash'
import type {ValidateFieldsError} from 'async-validator/dist-types'

export const assertSuccess = async <T>(result: T) => {
  return await new Promise<T>((resolve, reject) => {
    if (!_.isNil(result)) {
      ElMessage.success({ message: '操作成功' })
      resolve(result)
    } else {
      reject(result)
    }
  })
}
export const assertFormValidate = (callback: () => void) => {
  return (valid: boolean, fields: ValidateFieldsError | undefined) => {
    if (valid) {
      callback()
    } else {
      if (fields) {
        const messages: string[] = []
        for (const field in fields) {
          fields[field].forEach((error) => {
            if (error.message) {
              messages.push(error.message)
            }
          })
        }
        ElMessage.error({ message: messages.join('\n') })
      }
    }
  }
}

export class Observable<T> {
  observers: Array<(t: T) => void>

  constructor() {
    this.observers = []
  }

  subscribe(func: (t: T) => void) {
    this.observers.push(func)
  }

  unsubscribe(func: (t: T) => void) {
    this.observers = this.observers.filter((observer) => observer !== func)
  }

  notify(data: T) {
    this.observers.forEach((observer) => observer(data))
  }
}
