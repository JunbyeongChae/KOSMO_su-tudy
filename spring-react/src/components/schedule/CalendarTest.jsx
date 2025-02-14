import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid' 

const CalendarTest = () => {
    return (
        <FullCalendar
            plugins={[ dayGridPlugin ]}
            headerToolbar={{
                start: 'prev next today',
                center: 'title',
                end: '',
            }}
            height={'100vh'}
        />
    )
}
export default CalendarTest;