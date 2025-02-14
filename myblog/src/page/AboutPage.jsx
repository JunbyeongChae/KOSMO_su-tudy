import React from 'react'
import { Link } from 'react-router-dom'

const AboutPage = () => {
  return (
    <div className='text-3xl'>
        <div className='flex'>
          <Link to='/'>Home</Link>
        </div>
        <h1>AboutPage</h1>
    </div>
  )
}

export default AboutPage