import React, { useState, useEffect, useMemo} from 'react';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Button, InputGroup} from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const useFilterHook = ({
    filter = "",
    placeholder = "",
    onSubmit,
    btnOnClickClear,

}) => {
    const [search, setSearch] = useState('');
    const [query, setQuery] = useState('');

    const handleSearch = event =>{
        setSearch(event.target.value);
        setQuery(`${filter}.contains=${event.target.value}`);
    } 

    const Filter =  useMemo(() => {
        return () => (
            <AvForm onSubmit={onSubmit}>
                <AvGroup>
                    <InputGroup>
                        <AvInput
                        type="text"
                        name="search"
                        value={search}
                        onChange={handleSearch}
                        placeholder={placeholder}
                        />
                        <Button className="input-group-addon">
                        <FontAwesomeIcon icon="search" />
                        </Button>
                        <Button type="reset" className="input-group-addon" onClick={btnOnClickClear}>
                        <FontAwesomeIcon icon="trash" />
                        </Button>
                    </InputGroup>
                </AvGroup>
            </AvForm>
        )
    }, [])

    return{
        Filter,
        search,
        query,
        setSearch,
        setQuery
    }
}