import React, { SVGProps } from 'react';

export const TablerBus: React.FC<SVGProps<SVGSVGElement>> = props => (
    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" {...props}>
        <g fill="none" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}>
            <path d="M4 17a2 2 0 1 0 4 0a2 2 0 1 0-4 0m12 0a2 2 0 1 0 4 0a2 2 0 1 0-4 0"></path>
            <path d="M4 17H2V6a1 1 0 0 1 1-1h14a5 7 0 0 1 5 7v5h-2m-4 0H8"></path>
            <path d="m16 5l1.5 7H22M2 10h15M7 5v5m5-5v5"></path>
        </g>
    </svg>
);
