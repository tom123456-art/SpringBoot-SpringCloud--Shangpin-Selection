<template>
  <div class="ai-page">
    <div class="hero">
      <h1>AI Chat</h1>
      <p>Send a question to the configured AI endpoint.</p>
    </div>

    <el-card class="panel" shadow="never">
      <el-form @submit.prevent>
        <el-form-item>
          <el-input
            v-model="prompt"
            type="textarea"
            :rows="5"
            maxlength="1000"
            show-word-limit
            placeholder="Type your question"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="sendPrompt">Send</el-button>
          <el-button @click="clearChat">Clear</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="chat" shadow="never">
      <div v-if="messages.length === 0" class="empty">No messages yet.</div>
      <div v-for="item in messages" :key="item.id" class="bubble" :class="item.role">
        <div class="role">{{ item.role === 'user' ? 'Me' : 'AI' }}</div>
        <div class="content">{{ item.content }}</div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { nextTick, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { ChatAi } from '@/api/ai'

const prompt = ref('')
const loading = ref(false)
const messages = ref([])

const scrollToBottom = async () => {
  await nextTick()
  window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' })
}

const sendPrompt = async () => {
  const value = prompt.value.trim()
  if (!value) {
    ElMessage.warning('Please enter a question')
    return
  }
  messages.value.push({ id: Date.now(), role: 'user', content: value })
  loading.value = true
  prompt.value = ''
  await scrollToBottom()
  try {
    const { data } = await ChatAi({ prompt: value })
    messages.value.push({ id: Date.now() + 1, role: 'ai', content: data || 'No reply' })
  } finally {
    loading.value = false
    await scrollToBottom()
  }
}

const clearChat = () => {
  messages.value = []
}
</script>

<style scoped lang="scss">
.ai-page {
  min-height: 100%;
  padding: 24px;
  background: linear-gradient(180deg, #f6f8ff 0%, #ffffff 38%);
}

.hero {
  margin-bottom: 16px;
  h1 {
    margin: 0;
    font-size: 28px;
    color: #162033;
  }
  p {
    margin: 8px 0 0;
    color: #667085;
  }
}

.panel,
.chat {
  margin-bottom: 16px;
  border-radius: 16px;
}

.chat {
  min-height: 360px;
}

.empty {
  color: #98a2b3;
  text-align: center;
  padding: 48px 0;
}

.bubble {
  padding: 14px 16px;
  border-radius: 14px;
  margin-bottom: 12px;
  background: #f8fafc;
}

.bubble.ai {
  background: #eef4ff;
}

.role {
  font-size: 12px;
  color: #667085;
  margin-bottom: 6px;
}

.content {
  white-space: pre-wrap;
  line-height: 1.7;
  color: #1d2939;
}
</style>
